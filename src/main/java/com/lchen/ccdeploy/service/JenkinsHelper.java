package com.lchen.ccdeploy.service;

import com.lchen.ccdeploy.config.handler.GlobalSession;
import com.lchen.ccdeploy.model.JenkinsBuildHistory;
import com.lchen.ccdeploy.utils.JenkinsClient;
import com.offbytwo.jenkins.model.Build;
import com.offbytwo.jenkins.model.BuildResult;
import com.offbytwo.jenkins.model.BuildWithDetails;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static com.lchen.ccdeploy.model.JenkinsBuildHistory.buildHistory;

/**
 * @author : lchen
 * @date : 2019/5/29
 */
@Slf4j
@Component
public class JenkinsHelper implements InitializingBean {

    @Autowired
    private JenkinsClient jenkinsClient;
    @Autowired
    private JenkinsService jenkinsService;
    private static ScheduledExecutorService sch = Executors.newScheduledThreadPool(2);

    @Override
    public void afterPropertiesSet() {
        sch.scheduleAtFixedRate(this::run, 0, 6, TimeUnit.SECONDS);
    }

    private void run() {
        Set<String> contexts = GlobalSession.getContextSessions().keySet();
        contexts.forEach(this::updateJenkinsByContext);
    }

    public void updateJenkinsByContext(String context) {
        try {
            List<Build> builds = jenkinsClient.buildsByJob(context);
            builds.forEach(this::updateJenkinsBuild);
        } catch (IOException e) {
            log.error("获取应用{}的构建列表失败", context, e);
        }
    }

    private void updateJenkinsBuild(Build build) {
        try {
            BuildWithDetails details = build.details();
            boolean building = details.isBuilding();
            JenkinsBuildHistory jenkinsBuildHistory;
            if (building) {
                jenkinsBuildHistory = buildHistory(details, BuildResult.BUILDING);
            } else {
                jenkinsBuildHistory = buildHistory(details, details.getResult());
            }
            jenkinsService.createOrUpdate(jenkinsBuildHistory);
        } catch (IOException e) {
            log.error("", e);
        }
    }

    public void startBuild(@NonNull String context) {
        try {
            jenkinsClient.build(context);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
