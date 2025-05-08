package dgb.Mp.validation;

import dgb.Mp.Courierl.Dtos.CourielDtoToAdd;
import dgb.Mp.Courierl.enums.Couriel_Type;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;

public class CourielTypeValidator implements ConstraintValidator<ValidCouriel, CourielDtoToAdd> {
    @Override
    public boolean isValid(CourielDtoToAdd courielDtoToAdd, ConstraintValidatorContext constraintValidatorContext) {
        if (courielDtoToAdd == null || courielDtoToAdd.getType() == null) {
            return false;
        }

        Couriel_Type type = courielDtoToAdd.getType();
        // Disable default message for custom violations
        constraintValidatorContext.disableDefaultConstraintViolation();

        // Validate common required fields first
        boolean isValid = validateCommonFields(courielDtoToAdd, constraintValidatorContext);
if (!isValid) {
    switch (type) {
        case EXTERNAL_ARRIVED:
            isValid &= validateExternalArrived(courielDtoToAdd, constraintValidatorContext);
            break;
        case INTERNAL_ARRIVED:
            isValid &= validateInternalArrived(courielDtoToAdd, constraintValidatorContext);
            break;
        case INTERNAL_SENT:
            isValid &= validateInternalSent(courielDtoToAdd, constraintValidatorContext);
            break;
        case EXTERNAL_SENT:
            isValid &= validateExternalSent(courielDtoToAdd, constraintValidatorContext);
            break;
        default:
            constraintValidatorContext.buildConstraintViolationWithTemplate("Unsupported Couriel_Type: " + type)
                    .addConstraintViolation();
            return false;
    }
}
        return isValid;
    }

    private boolean validateCommonFields(CourielDtoToAdd courielDtoToAdd, ConstraintValidatorContext context) {
        boolean valid = true;

        if (courielDtoToAdd.getCourielNumber() == null) {
            addViolation(context, "courielNumber", "Couriel number is required");
            valid = false;
        }
        if (courielDtoToAdd.getSubject() == null || courielDtoToAdd.getSubject().isEmpty()) {
            addViolation(context, "subject", "Subject is required");
            valid = false;
        }
        if (courielDtoToAdd.getType() == null) {
            addViolation(context, "type", "Type is required");
            valid = false;
        }
        if (courielDtoToAdd.getPriority() == null) {
            addViolation(context, "priority", "Priority is required");
            valid = false;
        }
        if (courielDtoToAdd.getStatus() == null) {
            addViolation(context, "status", "Status is required");
            valid = false;
        }
        if (courielDtoToAdd.getArrivedDate() == null) {
            addViolation(context, "arrivedDate", "Arrived date is required");
            valid = false;
        }
        if (courielDtoToAdd.getDepartureDate() == null) {
            addViolation(context, "departureDate", "Departure date is required");
            valid = false;
        }
        if (courielDtoToAdd.getReturnDate() == null) {
            addViolation(context, "returnDate", "Return date is required");
            valid = false;
        }

        return valid;
    }
    private boolean validateExternalArrived(CourielDtoToAdd courielDtoToAdd, ConstraintValidatorContext context) {
        boolean valid = true;

        // Required fields specific to EXTERNAL_ARRIVED
        if (courielDtoToAdd.getFromExternal() == null || courielDtoToAdd.getFromExternal().isEmpty()) {
            addViolation(context, "fromExternal", "Minsistry sender is required for EXTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getToDirectionGeneralId() == null) {
            addViolation(context, "toDirectionGeneralId", "The Direction general reciever ID is required for EXTERNAL_ARRIVED");
            valid = false;
        }

        // Forbidden fields
        if (courielDtoToAdd.getFromDirectionGeneralId() != null) {
            addViolation(context, "fromDirectionGeneralId", "The Direction general sender ID must not be provided for EXTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getFromDivisionId() != null) {
            addViolation(context, "fromDivisionId", "The Division sender ID must not be provided for EXTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getFromDirectionId() != null) {
            addViolation(context, "fromDirectionId", "The Direction sender ID must not be provided for EXTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getFromSousDirectionId() != null) {
            addViolation(context, "fromSousDirectionId", "The Sous Direction sender ID must not be provided for EXTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getToExternal() != null && !courielDtoToAdd.getToExternal().isEmpty()) {
            addViolation(context, "toExternal", "The ministry to receive must not be provided for EXTERNAL_ARRIVED");
            valid = false;
        }

        return valid;
    }

    private boolean validateInternalArrived(CourielDtoToAdd courielDtoToAdd, ConstraintValidatorContext context) {
        boolean valid = true;

        // Required fields specific to INTERNAL_ARRIVED
        if (courielDtoToAdd.getFromDirectionGeneralId() == null) {
            addViolation(context, "fromDirectionGeneralId", "From Dgb ID is required for INTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getFromDivisionId() == null) {
            addViolation(context, "fromDivisionId", "From Division ID is required for INTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getFromDirectionId() == null) {
            addViolation(context, "fromDirectionId", "From Direction ID is required for INTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getFromSousDirectionId() == null) {
            addViolation(context, "fromSousDirectionId", "From Sous Direction ID is required for INTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getToDirectionGeneralId() == null) {
            addViolation(context, "toDirectionGeneralId", "To Diercetion general ID is required for INTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getToDivisionId() == null) {
            addViolation(context, "toDivisionId", "To Division ID is required for INTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getToDirectionId() == null) {
            addViolation(context, "toDirectionId", "To Direction ID is required for INTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getToSousDirectionId() == null) {
            addViolation(context, "toSousDirectionId", "To Sous Direction ID is required for INTERNAL_ARRIVED");
            valid = false;
        }

        // Forbidden fields
        if (courielDtoToAdd.getFromExternal() != null && !courielDtoToAdd.getFromExternal().isEmpty()) {
            addViolation(context, "fromExternal", "From external must not be provided for INTERNAL_ARRIVED");
            valid = false;
        }
        if (courielDtoToAdd.getToExternal() != null && !courielDtoToAdd.getToExternal().isEmpty()) {
            addViolation(context, "toExternal", "To external must not be provided for INTERNAL_ARRIVED");
            valid = false;
        }

        return valid;
    }

    private boolean validateInternalSent(CourielDtoToAdd dto, ConstraintValidatorContext context) {
        // Same as INTERNAL_ARRIVED for now—adjust if different
        return validateInternalArrived(dto, context);
    }

    private boolean validateExternalSent(CourielDtoToAdd courielDtoToAdd, ConstraintValidatorContext context) {
        boolean valid = true;

        // Required fields specific to EXTERNAL_SENT
        if (courielDtoToAdd.getToExternal() == null || courielDtoToAdd.getToExternal().isEmpty()) {
            addViolation(context, "toExternal", "To external is required for EXTERNAL_SENT");
            valid = false;
        }
        if (courielDtoToAdd.getFromDirectionGeneralId() == null) {
            addViolation(context, "fromDirectionGeneralId", "From Dgb ID is required for EXTERNAL_SENT");
            valid = false;
        }
        if (courielDtoToAdd.getFromDivisionId() == null) {
            addViolation(context, "fromDivisionId", "From Division ID is required for EXTERNAL_SENT");
            valid = false;
        }
        if (courielDtoToAdd.getFromDirectionId() == null) {
            addViolation(context, "fromDirectionId", "From Direction ID is required for EXTERNAL_SENT");
            valid = false;
        }
        if (courielDtoToAdd.getFromSousDirectionId() == null) {
            addViolation(context, "fromSousDirectionId", "From Sous Direction ID is required for EXTERNAL_SENT");
            valid = false;
        }

        // Forbidden fields
        if (courielDtoToAdd.getToDirectionGeneralId() != null) {
            addViolation(context, "toDirectionGeneralId", "To Direction General ID must not be provided for EXTERNAL_SENT");
            valid = false;
        }
        if (courielDtoToAdd.getToDivisionId() != null) {
            addViolation(context, "toDivisionId", "To Division ID must not be provided for EXTERNAL_SENT");
            valid = false;
        }
        if (courielDtoToAdd.getToDirectionId() != null) {
            addViolation(context, "toDirectionId", "To Direction ID must not be provided for EXTERNAL_SENT");
            valid = false;
        }
        if (courielDtoToAdd.getToSousDirectionId() != null) {
            addViolation(context, "toSousDirectionId", "To Sous Direction ID must not be provided for EXTERNAL_SENT");
            valid = false;
        }

        return valid;
    }
    private void addViolation(ConstraintValidatorContext context, String field, String message) {
        context.buildConstraintViolationWithTemplate(message)
                .addPropertyNode(field)
                .addConstraintViolation();
    }
}
