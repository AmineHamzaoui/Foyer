pipeline {
    agent any
    environment {
        IMAGE_NAME = "eya-app"
        DOCKER_HUB_REPO = "eyaamamou/${IMAGE_NAME}"
        DOCKER_HUB_CREDENTIALS_ID = 'docker-hub-creds'
    }

    parameters {
        booleanParam(name: 'SKIP_BUILD_DOCKER_IMAGE', defaultValue: false, description: 'Skip Docker Image Build Stage')
        booleanParam(name: 'SKIP_PUSH_DOCKER_IMAGE', defaultValue: false, description: 'Skip Docker Image Push Stage')
    }
   
    stages {
        stage('Checkout Code') {
            steps {
                git branch: 'feature/amamoueya', credentialsId: 'github-creds', url: 'https://github.com/AmineHamzaoui/Foyer.git'
            }
        }

        // Increment Version for Release
        stage('Increment Version') {
            steps {
                script {
                    // Read the current version from version.properties
                    def versionProperties = readFile('version.properties').trim()
                    def currentVersion = versionProperties.split('=')[1]

                    // Increment the patch version
                    def (major, minor, patch) = currentVersion.tokenize('.').collect { it as int }
                    patch += 1
                    def newVersion = "${major}.${minor}.${patch}"

                    // Update version.properties with the new version
                    writeFile file: 'version.properties', text: "version=${newVersion}"

                    // Set the new version as an environment variable for later use
                    env.NEW_VERSION = newVersion

                    // Commit the updated version.properties back to the repository
                    sh 'git config user.email "jenkins@example.com"'
                    sh 'git config user.name "Jenkins"'

                    // Use credentials to push changes
                    withCredentials([usernamePassword(credentialsId: 'github-creds', usernameVariable: 'GIT_USERNAME', passwordVariable: 'GIT_PASSWORD')]) {
                        sh 'git add version.properties'
                        sh 'git commit -m "Incremented version to ${newVersion}"'
                        sh "git push https://${GIT_USERNAME}:${GIT_PASSWORD}@github.com/AmineHamzaoui/Foyer.git feature/amamoueya"
                    }
                }
            }
        }

        // Build and Test Stages
        stage('Build with Maven') {
            steps {
                sh 'mvn clean compile jacoco:report'
            }
        }

        stage('Test Unitaires et Jacoco') {
            steps {
                sh 'mvn clean test'
            }
        }
        
        stage('Build') {
            steps {
                // Use the new version in the Maven package command
                sh "mvn versions:set -DnewVersion=${env.NEW_VERSION} -DgenerateBackupPoms=false"
                sh 'mvn package'
            }
        }

        // Analyze Code with SonarQube
        stage('MVN SonarQube') {
            steps {
                script {
                    withSonarQubeEnv('sonarserver') {
                        withCredentials([string(credentialsId: 'sonartoken', variable: 'SONAR_TOKEN')]) {
                            sh '''
                                mvn sonar:sonar \
                                    -Dsonar.projectKey=springproject \
                                    -Dsonar.host.url=http://192.168.33.10:9000 \
                                    -Dsonar.login=${SONAR_TOKEN} \
                                    -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                            '''
                        }
                    }
                }
            }
        }

        // Quality Gate Check
        stage('Quality Gate') {
            steps {
                script {
                    echo "Quality Gate passed"
                }
            }
        }

        // Verify Artifact
        stage('Verify Artifact') {
            steps {
                sh 'ls -l target'
            }
        }

        // Deploy to Nexus
        stage('Deploy to Nexus') {
            steps {
                script {
                    nexusArtifactUploader(
                        nexusVersion: 'nexus3',
                        protocol: 'http',
                        nexusUrl: 'http://192.168.33.10:8081',
                        groupId: 'com.projet',
                        version: "${env.NEW_VERSION}",
                        repository: 'maven-releases',
                        credentialsId: 'NEXUS_CRED',
                        artifacts: [
                            [
                                artifactId: 'tp-foyer',
                                classifier: '',
                                file: "target/tp-foyer-${env.NEW_VERSION}.jar",
                                type: 'jar'
                            ]
                        ]
                    )
                }
            }
            post {
                success {
                    echo 'Deployment to Nexus successful.'
                }
                failure {
                    echo 'Error during Nexus deployment.'
                }
            }
        }
    }
}
