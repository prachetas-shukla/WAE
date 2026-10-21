package edu.srmist.outpass.worker;

import io.camunda.client.annotation.JobWorker;
import io.camunda.client.annotation.Variable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Map;

@Component
public class OutpassWorkers {

    private static final Logger log = LoggerFactory.getLogger(OutpassWorkers.class);

    @JobWorker(type = "check-outpass")
    public Map<String, Object> checkOutpass(@Variable String regNo,
                                           @Variable String outDate,
                                           @Variable String returnDate) {
        LocalDate out = LocalDate.parse(outDate);
        LocalDate ret = LocalDate.parse(returnDate);

        long nights = ChronoUnit.DAYS.between(out, ret);
        boolean valid = !ret.isBefore(out);
        boolean approved = valid && nights == 0;

        return Map.of("nights", nights, "valid", valid, "approved", approved);
    }

    @JobWorker(type = "issue-outpass")
    public Map<String, Object> issueOutpass(@Variable String regNo,
                                            @Variable String outDate,
                                            @Variable String studentName,
                                            @Variable String parentPhone,
                                            @Variable Boolean approved,
                                            @Variable(required = false) String wardenRemarks) {
        String passStatus = Boolean.TRUE.equals(approved) ? "ISSUED" : "REJECTED";
        String passNumber = "OP-" + regNo + "-" + outDate;

        log.info("SMS to parent ({}): Out-pass for {} ({}) is {}. Pass Number: {}. Remarks: {}",
                parentPhone, studentName, regNo, passStatus, passNumber, wardenRemarks != null ? wardenRemarks : "None");

        return Map.of("passStatus", passStatus, "passNumber", passNumber);
    }
}
