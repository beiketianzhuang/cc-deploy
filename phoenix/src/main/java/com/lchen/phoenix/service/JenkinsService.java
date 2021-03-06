package com.lchen.phoenix.service;

import com.lchen.phoenix.dao.JenkinsBuildHistoryRepository;
import com.lchen.phoenix.model.DeploymentVersion;
import com.lchen.phoenix.model.JenkinsBuild;
import com.lchen.phoenix.model.JenkinsBuildHistory;
import com.lchen.phoenix.model.constants.JenkinsBuildStatus;
import com.lchen.phoenix.utils.BuildProgress;
import com.lchen.phoenix.utils.JenkinsClient;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;

import static com.lchen.phoenix.model.constants.JenkinsBuildStatus.BUILDING;
import static java.util.stream.Collectors.toList;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Slf4j
@Service
public class JenkinsService {

    @Autowired
    private JenkinsClient jenkinsClient;

    @Autowired
    private JenkinsBuildHistoryRepository jenkinsBuildHistoryRepository;

    public List<JenkinsBuildHistory> builds(String context) {
        return jenkinsBuildHistoryRepository.findLatelyByVersionAndJobName(context);
    }


    public List<JenkinsBuild> listJenkinsBuilds(@NonNull String context) {
        List<JenkinsBuildHistory> builds = builds(context);
        return builds.stream().map(this::jenkinsBuildBuilder).collect(toList());
    }

    private JenkinsBuild jenkinsBuildBuilder(JenkinsBuildHistory jenkinsBuildHistory) {
        JenkinsBuildStatus buildStatus = jenkinsBuildHistory.getBuildStatus();
        JenkinsBuild jenkinsBuild = JenkinsBuild.apply(jenkinsBuildHistory);
        if (buildStatus == BUILDING) {
            try {
                BuildProgress buildProgress = jenkinsClient.buildProgress(jenkinsBuildHistory.getJobName(), jenkinsBuildHistory.getVersion());
                if (buildProgress.getExecutor() != null) {
                    jenkinsBuild.setPercentage(buildProgress.getExecutor().getProgress());
                }
            } catch (IOException e) {
                log.error("获取版本{} 应用{}的构建进度失败", jenkinsBuildHistory.getVersion(), jenkinsBuildHistory.getJobName(), e);
            }
        }
        return jenkinsBuild;
    }

    public List<DeploymentVersion> deployVersions(@NonNull String context) {
        List<JenkinsBuildHistory> jenkinsBuildHistories = jenkinsBuildHistoryRepository.findDeployVersionAndJobName(context);

        return  null;
    }

    public void createOrUpdate(JenkinsBuildHistory buildHistory) {
        JenkinsBuildHistory jenkinsBuildHistory = jenkinsBuildHistoryRepository.
                findByVersionAndJobName(buildHistory.getVersion(), buildHistory.getJobName());
        if (jenkinsBuildHistory == null) {
            jenkinsBuildHistoryRepository.saveAndFlush(buildHistory);
        } else {
            jenkinsBuildHistory.setBuildTime(buildHistory.getBuildTime());
            jenkinsBuildHistory.setBuildStatus(buildHistory.getBuildStatus());
            jenkinsBuildHistoryRepository.saveAndFlush(jenkinsBuildHistory);
        }
    }
}
