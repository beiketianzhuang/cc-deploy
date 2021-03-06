package com.lchen.phoenix.model.req;

import com.lchen.phoenix.model.DeployType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : lchen
 * @date : 2019/7/22
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DeploymentReq {
    private String context;
    private Integer deployVersion;
    private DeployType deployType;
}
