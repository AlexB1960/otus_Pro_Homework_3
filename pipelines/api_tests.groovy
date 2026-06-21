
timeout(120) {
   node("ansible") {
     currentBuild.description = "Running api-tests on Jenkins"

     stage("Checkout") {
         checkout scm  //стягиваем проект
     }
     stage("Running api-tests on Jenkins") {

        sh "docker run --rm tests_api:1.0"
     }
     stage("Allure report") {
         sh "tar -czf ajjure-results.tar.gz -C allure-results ." //архивация json-файлов текущещей джобы в tar-архив
         archiveArtifacts artifacts: "*.tar.gz", //пушим архив как артифакт текущей джобы
                 allowEmptyArchive: true, //пустой архив разрешается к пушу
                 fingerprint: true,
                 onlyIfSuccessful: true //только если джоба прошла успешно (не упала), но тестам разрешено падать
         allure(
                 results: [[path: "allure-results"]], //результаты искать в папке allure-results
                 disable: false,
                 reportBuildPolicy: "ALWAYS" //всегда включаем и всегла собираем
         )
     }
   }
}
