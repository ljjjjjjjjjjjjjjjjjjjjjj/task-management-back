ALTER TABLE employees
ADD COLUMN image_id UUID,
ADD CONSTRAINT fk_image
FOREIGN KEY (image_id) REFERENCES employee_images(image_id) ON DELETE SET NULL;