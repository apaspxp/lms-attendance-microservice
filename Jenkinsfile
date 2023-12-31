  pipeline{
    agent any
    tools{
    maven 'maven'
    }
    stages{
//        stage('build maven'){
//           steps{
//               checkout scmGit(branches: [[name: '*/lms-jenkins-pipeline']], extensions: [], userRemoteConfigs: [[credentialsId: 'git_credential', url: 'https://github.com/apaspxp/lms-helmchart.git']])
//                sh 'mvn clean install'
//                }
//             }
//        stage('Database Connection') {
//                    steps {
//                        script {
//                        // Use Jenkins credentials to get the MySQL username and password
//                        withCredentials([usernamePassword(credentialsId: 'b6e6b90d-21c4-4057-b364-01f059b29056', usernameVariable: 'DB_USERNAME', passwordVariable: 'DB_PASSWORD')]) {
//                            // Connect to the MySQL database and perform operations
//                            // Replace 'your_database_name', 'your_mysql_host', and 'your_table_name' with actual values
//                            def dbName = 'LMSATTENDANCESERVICE'
//                            def dbHost = 'localhost'  // If running on localhost, you can use 'localhost' or '127.0.0.1'
//                            def dbPort = '3306'  // Default MySQL port
//
//                            def query = "SELECT * FROM your_table_name;"
//                            def command = "mysql -u ${DB_USERNAME} -p${DB_PASSWORD} -h ${dbHost} -P ${dbPort} ${dbName} -e \"${query}\""
//
//                            // Execute the SQL query using the 'sh' step
//                            def result = sh(returnStdout: true, script: command).trim()
//                            echo "Query Result:\n${result}"
//                        }
//                    }
//                }
//              }
       // stage('Build and Package') {
       //     steps {                 // Build your project and skip tests
       //         script {
       //             sh "mvn clean package -DskipTests"
       //         }
       //     }
       // }
       stage('Build Docker Image') {
          steps {
            script {
               withCredentials([usernamePassword(credentialsId: 'DockerHub_Credential', passwordVariable: 'DOCKERHUB_PASSWORD', usernameVariable: 'DOCKERHUB_USERNAME')]) {
               // Generate a timestamp or version number for the image tag
               def timestamp = new Date().format("yyyyMMdd_HHmmss")
               def imageTag = "lms-attendance-service:${timestamp}"

              // Build the Docker image using the spring-boot:build-image Maven goal
               sh "mvn spring-boot:build-image -Dspring-boot.build-image.imageName=apaspxp/${imageTag}"
              //    sh "mvn spring-boot:build-image -Dspring-boot.build-image.imageName=apaspxp/lms-attendance-service:latest"

             // Log in to Docker Hub using the credentials
                sh "docker login -u $DOCKERHUB_USERNAME -p $DOCKERHUB_PASSWORD"
                // sh "echo $DOCKERHUB_PASSWORD | docker login --username $DOCKERHUB_USERNAME --password-stdin"

                // Push the Docker image to a registry
                sh "docker push apaspxp/${imageTag}"
                // sh "docker push apaspxp/lms-attendance-service:latest"

                // Tag the image as "latest"
                sh "docker tag apaspxp/${imageTag} apaspxp/lms-attendance-service:latest"
                sh "docker push apaspxp/lms-attendance-service:latest"

                // Print the image tag for reference
                echo "Docker image tag: ${imageTag}"
                }
              }
            }
        }
       stage('Deploy to Kubernetes'){
            steps{
                script {
                    withCredentials([file(credentialsId: 'Kubernetes_Credentials', variable: 'KUBECONFIG')]) {
                    //Cleanup the resources
                    sh "kubectl delete -f lms-attendance-service-service.yaml --ignore-not-found"
                    //Apply the new manifest file
                    sh "kubectl apply -f lms-attendance-service-service.yaml"
                   }
                }
            }
        }
     }
  }

