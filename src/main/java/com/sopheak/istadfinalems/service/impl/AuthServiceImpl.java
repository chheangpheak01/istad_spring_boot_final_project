package com.sopheak.istadfinalems.service.impl;
import com.sopheak.istadfinalems.entities.Address;
import com.sopheak.istadfinalems.entities.Employee;
import com.sopheak.istadfinalems.entities.Project;
import com.sopheak.istadfinalems.entities.emun.EmployeeStatus;
import com.sopheak.istadfinalems.entities.emun.GenderStatus;
import com.sopheak.istadfinalems.exception.EmployeeNotFoundException;
import com.sopheak.istadfinalems.mapper.EmployeeMapStruct;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeCreateDto;
import com.sopheak.istadfinalems.model.dto.employee.EmployeeResponseDto;
import com.sopheak.istadfinalems.model.dto.keycloak.ResetPasswordDto;
import com.sopheak.istadfinalems.repository.*;
import jakarta.ws.rs.core.Response;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.apache.http.HttpStatus;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);
    private final Keycloak keycloak;
    private final EmployeeRepository employeeRepository;
    private final EmployeeMapStruct employeeMapStruct;
    private final DepartmentRepository departmentRepository;
    private final JobPositionRepository jobPositionRepository;
    private final ProjectRepository projectRepository;

    @Value("${spring.keycloak.main-realm}")
    private String realm;

    public EmployeeResponseDto registerEmployee(EmployeeCreateDto employeeCreateDto) {
        if (employeeRepository.existsEmployeesByEmail(employeeCreateDto.email())) {
            throw new IllegalArgumentException("Email already exists: " + employeeCreateDto.email());
        }

        UserRepresentation kcUser = new UserRepresentation();

        kcUser.setUsername(employeeCreateDto.email());
        kcUser.setFirstName(employeeCreateDto.name());
        kcUser.setLastName("");
        kcUser.setEmail(employeeCreateDto.email());
        kcUser.setEnabled(true);
        kcUser.setEmailVerified(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(employeeCreateDto.password());
        kcUser.setCredentials(List.of(credential));

        try (Response response = keycloak.realm(realm).users().create(kcUser)) {
            if (response.getStatus() == HttpStatus.SC_CREATED) {

                String keycloakUuid = getUserIdFromKeycloak(response);

                Employee em = new Employee();
                em.setUuid(keycloakUuid);
                em.setEmployeeId("EMP-" + java.time.Year.now().getValue() + "-" + (int)(Math.random() * 9000 + 1000));
                em.setName(employeeCreateDto.name());
                em.setEmail(employeeCreateDto.email());
                em.setPassword(employeeCreateDto.password());
                em.setPhoneNumber(employeeCreateDto.phoneNumber());
                em.setDob(employeeCreateDto.dob());
                em.setSalary(employeeCreateDto.salary());
                em.setHireDate(employeeCreateDto.hireDate());
                em.setBiography(employeeCreateDto.biography());
                em.setProfileImage(employeeCreateDto.profileImage());
                em.setIsDeleted(false);
                em.setStatus(EmployeeStatus.ACTIVE);
                em.setGender(GenderStatus.valueOf(employeeCreateDto.gender().toUpperCase()));
                departmentRepository.findByUuidAndIsDeletedFalse(employeeCreateDto.departmentUuid())
                        .ifPresent(em::setDepartment);
                jobPositionRepository.findByUuidAndIsDeletedFalse(employeeCreateDto.positionUuid())
                        .ifPresent(em::setJobPosition);
                if (employeeCreateDto.address() != null) {
                    Address address = new Address();
                    address.setUuid(java.util.UUID.randomUUID().toString());
                    address.setStreet(employeeCreateDto.address().street());
                    address.setCity(employeeCreateDto.address().city());
                    address.setProvince(employeeCreateDto.address().province());
                    address.setCountry(employeeCreateDto.address().country());
                    address.setIsDeleted(false);
                    em.setAddress(address);
                }
                if (employeeCreateDto.projectUuids() != null && !employeeCreateDto.projectUuids().isEmpty()) {
                    List<Project> projects = projectRepository.findAllByUuidIn(new java.util.ArrayList<>(employeeCreateDto.projectUuids()));
                    em.setProjects(new java.util.HashSet<>(projects));
                }
                employeeRepository.save(em);
                return employeeMapStruct.mapFromEmployeeToEmployeeResponseDto(em);
            } else {
                throw new RuntimeException("Keycloak creation failed Status: " + response.getStatus());
            }
        } catch (Exception e) {
            throw new RuntimeException("Registration failed: " + e.getMessage());
        }
    }

    private String getUserIdFromKeycloak(Response response) {
        return response
                .getLocation()
                .getPath()
                .replaceAll(".*/([^/]+)$", "$1");
    }

    public String resetPassword(ResetPasswordDto resetPasswordDto) {
        if (!resetPasswordDto.newPassword().equals(resetPasswordDto.confirmPassword())) {
            throw new IllegalArgumentException("Passwords do not match!");
        }
        try {
            UserRepresentation user = keycloak.realm(realm).users().get(resetPasswordDto.userId()).toRepresentation();
            if (user != null) {
                CredentialRepresentation credential = new CredentialRepresentation();
                credential.setType(CredentialRepresentation.PASSWORD);
                credential.setValue(resetPasswordDto.newPassword());
                credential.setTemporary(false);
                keycloak.realm(realm).users().get(resetPasswordDto.userId())
                        .resetPassword(credential);
                log.info("Password reset successful for user ID: {}", resetPasswordDto.userId());
                return user.getId();
            }
        } catch (Exception e) {
            log.error("Failed to reset password for user {}: {}", resetPasswordDto.userId(), e.getMessage());
        }
        throw new EmployeeNotFoundException("Employee not found with ID: " + resetPasswordDto.userId());
    }

    public String forgotPassword(String email) {
        try {
            List<UserRepresentation> users = keycloak.realm(realm).users().searchByEmail(email, true);

            if (!users.isEmpty()) {
                UserRepresentation user = users.get(0);
                sendEmailVerify(user.getId(), List.of("UPDATE_PASSWORD"));
                log.info("Forgot password email triggered for: {}", email);
                return user.getId();
            }
        } catch (Exception e) {
            log.error("Forgot password process failed for email {}: {}", email, e.getMessage());
        }
        throw new EmployeeNotFoundException("Employee not found with email: " + email);
    }

    private void sendEmailVerify(String userId, List<String> actions) {
        keycloak.realm(realm).users().get(userId).executeActionsEmail(actions);
    }
}
