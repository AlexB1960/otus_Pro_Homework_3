
timeout(120) {
   node("ansible") {
     currentBuild.description = "Running api-tests on Jenkins"

     stage("Running api-tests on Jenkins") {
        sh "run tests_api:1.0"
     }
   }
}
