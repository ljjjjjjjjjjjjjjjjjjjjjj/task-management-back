package com.devbridge.learning.Apptasks.services;


import com.devbridge.learning.Apptasks.exceptions.EntityNotFoundException;
import com.devbridge.learning.Apptasks.models.Employee;
import com.devbridge.learning.Apptasks.models.ImageEmployee;
import com.devbridge.learning.Apptasks.repositories.EmployeeRepository;
import com.devbridge.learning.Apptasks.repositories.ImageEmployeeRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ImageEmployeeService {

    private final ImageService imageService;
    private final ImageEmployeeRepository imageEmployeeRepository;
    private final EmployeeRepository employeeRepository;

    public static final String EMPLOYEE_NOT_FOUND = "Employee by the given id not found";
    private static final String EMPLOYEE_ALREADY_HAS_IMAGE = "This employee already has an image.";

    public ImageEmployee createEmployeeImage(MultipartFile file, String title, UUID employeeId) throws IOException {
        Employee existingEmpoyee = validateEmployeeId(employeeId);

        imageEmployeeRepository.findByEmployeeId(employeeId).ifPresent(existingImage -> {
            throw new IllegalStateException(EMPLOYEE_ALREADY_HAS_IMAGE);
        });

        byte[] resizedImage = imageService.resizeImage(file, 50, 50);

        ImageEmployee employeeImage = ImageEmployee.builder()
                .imageId(UUID.randomUUID())
                .imageTitle(title)
                .imageData(resizedImage)
                .employeeId(employeeId)
                .build();

        imageEmployeeRepository.create(employeeImage);
        employeeRepository.updateImageIdForEmployee(existingEmpoyee.getEmployeeId(), employeeImage.getImageId());

        return employeeImage;
    }

    public ImageEmployee getEmployeeImageByEmployeeId(UUID employeeId) {
        return imageEmployeeRepository.findByEmployeeId(employeeId).orElse(null);
    }

    public ImageEmployee getEmployeeImageByImageId(UUID imageId) {
        return imageEmployeeRepository.findByImageId(imageId).orElse(null);
    }

    public List<ImageEmployee> getAllImages() {
        return imageEmployeeRepository.findAll();
    }

    public void deleteEmployeeImage(UUID imageId) {
        imageEmployeeRepository.deleteByImageId(imageId);
    }


    // validation and other methods
    private Employee validateEmployeeId(UUID employeeId) {
        Employee existingEmployee = employeeRepository.findById(employeeId)
                .orElseThrow(() -> new EntityNotFoundException(EMPLOYEE_NOT_FOUND));
        return existingEmployee;
    }

    public void assignImageToEmployee(UUID employeeId, UUID imageId) {
        employeeRepository.updateImageIdForEmployee(employeeId, imageId);
    }

}
