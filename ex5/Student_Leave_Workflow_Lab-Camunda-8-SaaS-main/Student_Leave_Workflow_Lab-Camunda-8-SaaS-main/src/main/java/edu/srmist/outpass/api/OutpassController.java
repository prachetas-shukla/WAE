package edu.srmist.outpass.api;

import io.camunda.client.CamundaClient;
import io.camunda.client.api.response.ProcessInstanceEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class OutpassController {

    @Autowired
    private CamundaClient camundaClient;

    @PostMapping("/outpass")
    public Map<String, Object> applyOutpass(@RequestBody OutpassRequest request) {
        ProcessInstanceEvent instance = camundaClient.newCreateInstanceCommand()
                .bpmnProcessId("hostel-outpass")
                .latestVersion()
                .variables(request)
                .send()
                .join();

        return Map.of("processInstanceKey", instance.getProcessInstanceKey());
    }
}
