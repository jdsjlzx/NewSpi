package com.baidu.spi;

import com.android.build.gradle.AppExtension;
import com.android.build.gradle.api.ApplicationVariant;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.Task;
import org.gradle.api.file.FileCollection;
import org.gradle.api.specs.Spec;

import java.io.File;

//  https://zhuanlan.zhihu.com/p/515455819
public class SpiPlugin implements Plugin<Project> {
    @Override
    public void apply(Project project) {
        System.out.println("这是我的第一个SpiPlugin插件!");
        project.afterEvaluate(new Action<Project>() {

            @Override
            public void execute(Project project) {
                boolean isApp = project.getPlugins().hasPlugin("com.android.application");
                if (!isApp) {
                    return;
                }

                AppExtension android = project.getExtensions().findByType(AppExtension.class);
                for (ApplicationVariant variant : android.getApplicationVariants()) {
                    final File spiRoot = project.file(getSpiRootPath(project, variant));
                    System.out.println("SpiPlugin spiRoot " + spiRoot.getAbsolutePath());
                    final File dstDir = variant.getJavaCompileProvider().get().getDestinationDirectory().get().getAsFile();
                    System.out.println("SpiPlugin dstDir " + dstDir.getAbsolutePath());
                    final FileCollection classPath = project.files(android.getBootClasspath(), variant.getJavaCompileProvider().get().getClasspath(), dstDir);
                    System.out.println("SpiPlugin classPath " + classPath.getAsPath());
                    SpiTask spiTask = project.getTasks().create(newTaskName(variant), SpiTask.class, new Action<SpiTask>() {
                        @Override
                        public void execute(SpiTask task) {
                            task.setClassPath(task.getClassPath().plus(classPath));
                            task.setSourceDir(dstDir);
                            task.setServicesDir(spiRoot);
                            task.getOutputs().upToDateWhen(new Spec<Task>() {
                                @Override
                                public boolean isSatisfiedBy(Task task) {
                                    return false;
                                }
                            });
                        }
                    });
                    System.out.println("SpiPlugin variant.getJavaCompileProvider().get() " + variant.getJavaCompileProvider().get());
                    System.out.println("SpiPlugin variant.getAssembleProvider().get() " + variant.getAssembleProvider().get());
                    spiTask.mustRunAfter(variant.getJavaCompileProvider().get());
                    //Reason: Task ':app:processDebugJavaRes' uses this output of task ':app:generateServiceRegistrydebug' without declaring an explicit or implicit dependency. This can lead to incorrect results being produced, depending on what order the tasks are executed.
                    spiTask.mustRunAfter(variant.getProcessJavaResourcesProvider().get());
                    spiTask.mustRunAfter(":app:dexBuilderDebug");
                    variant.getAssembleProvider().get().dependsOn(spiTask);
                    if (variant.getInstallProvider() != null) {
                        Task task = variant.getInstallProvider().get();
                        System.out.println("SpiPlugin getInstallProvider task " + task.toString());
                        task.dependsOn(spiTask);
                    }
                }

            }
        });
    }

    private String getSpiRootPath(Project project, ApplicationVariant variant) {
        return project.getBuildDir() + "/intermediates/spi/" + variant.getDirName() + "/services";
    }

    private String newTaskName(ApplicationVariant variant) {
        return "generateServiceRegistry" + variant.getName();
    }
}