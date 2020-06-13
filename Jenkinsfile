pipeline {
    agent any

    stages {
        stage('Clone sources') {
            steps {
                git 'https://github.com/skjalal/javasparkexecutor'
            }
        }

        stage('Compile') {
            tools {
                gradle 'gradle4'
            }
            steps {
                sh 'gradle clean compileJava test'
            }
        }
    }
}