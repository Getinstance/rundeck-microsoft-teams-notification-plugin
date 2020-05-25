import com.dtolabs.rundeck.plugins.notification.NotificationPlugin
import groovy.json.JsonOutput


rundeckPlugin(NotificationPlugin){

    title="Microsoft Teams notification Plugin"
    description="Allows to set up notification for Microsoft Teams chats for a channel, via Webhook URL. To use it you will have to obtain webhook for your channel first and setit up."

    onstart { Map execution, Map configuration ->
        type = "START"
        color = "696969"
        appname = [execution.context.option.Appname, execution.context.option.App_name, execution.context.option.ENV].findAll().join(' ')
        info_job = [execution.context.option.url_nexus, execution.context.option.ACTION, execution.context.option.BRANCH_NAME].findAll().join(' ')
        json_payload = JsonOutput.toJson([
                title: "${execution.job.name} - ${execution.user} iniciou o deploy de ${appname}",
                summary: "${execution.job.name}",
                text: "${info_job}",
                themeColor: "${color}",
                potentialAction: [
                        [
                                "@context": "http://schema.org",
                                "@type": "ViewAction",
                                name: "Olha o job aqui!",
                                target: ["${execution.href}"]
                        ]
                ]
        ])
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${json_payload}' https://outlook.office.com/webhook/91744d32-9381-4748-bf35-4a3b3ba211a5@5a86b3fb-4213-49cd-b4d6-be91482ad3c0/IncomingWebhook/4bcf50721c1e423184279f44e29fbc24/4955d7e7-9d00-474a-b168-0c2e54f84f7e" ].execute().text

        return true
    }

    onfailure { Map execution, Map configuration ->
        type = "FAILURE"
        color = "E81123"
        appname = [execution.context.option.Appname, execution.context.option.App_name, execution.context.option.ENV].findAll().join(' ')
        info_job = [execution.context.option.url_nexus, execution.context.option.ACTION, execution.context.option.BRANCH_NAME].findAll().join(' ')
        //Single argument, the configuration properties are available automatically
        json_payload = JsonOutput.toJson([
                title: "${execution.job.name} - ${execution.user} quebrou o deploy de ${appname}",
                summary: "${execution.job.name}",
                text: "${info_job}",
                themeColor: "${color}",
                potentialAction: [
                        [
                                "@context": "http://schema.org",
                                "@type": "ViewAction",
                                name: "Olha o job aqui!",
                                target: ["${execution.href}"]
                        ]
                ]
        ])
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${json_payload}' https://outlook.office.com/webhook/91744d32-9381-4748-bf35-4a3b3ba211a5@5a86b3fb-4213-49cd-b4d6-be91482ad3c0/IncomingWebhook/4bcf50721c1e423184279f44e29fbc24/4955d7e7-9d00-474a-b168-0c2e54f84f7e" ].execute().text

        return true
    }

    onsuccess { Map execution, Map configuration ->
        type = "SUCCESS"
        color = "228B22"
        appname = [execution.context.option.Appname, execution.context.option.App_name, execution.context.option.ENV].findAll().join(' ')
        info_job = [execution.context.option.url_nexus, execution.context.option.ACTION, execution.context.option.BRANCH_NAME].findAll().join(' ')
        //with no args, there is a "configuration" and an "execution" variable in the context
        json_payload = JsonOutput.toJson([
                title: "${execution.job.name} - ${execution.user} conseguiu fazer o deploy de ${appname}",
                text: "${info_job}",
                themeColor: "${color}",
                potentialAction: [
                        [
                                "@context": "http://schema.org",
                                "@type": "ViewAction",
                                name: "Olha o job aqui!",
                                target: ["${execution.href}"]
                        ]
                ]
        ])
        process = [ 'bash', '-c', "curl -v -k -X POST -H \"Content-Type: application/json\" -d '${json_payload}' https://outlook.office.com/webhook/91744d32-9381-4748-bf35-4a3b3ba211a5@5a86b3fb-4213-49cd-b4d6-be91482ad3c0/IncomingWebhook/4bcf50721c1e423184279f44e29fbc24/4955d7e7-9d00-474a-b168-0c2e54f84f7e" ].execute().text

        return true
    }
}
