package com.mirego.kword

import org.gradle.api.Plugin
import org.gradle.api.Project

class KWordPlugin implements Plugin<Project> {
    private static final String TASKS_GROUP = 'KWord'

    @Override
    void apply(Project project) {
        KWordExtension extension = project.extensions.create('kword', KWordExtension, project)

        KWordEnumGenerate generateTask = project.task('kwordGenerateEnum', type: KWordEnumGenerate, group: TASKS_GROUP,
            description: 'Generate keys enum based on json translation file.')

    }
}
