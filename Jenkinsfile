pipeline {
    agent any

    stages {
        stage('Compile') {
            steps {
                sh './gradlew clean compileJava test'
            }
        }
    }
}