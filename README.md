# JUG Demo
 Example project created to demonstrate some of the flowable engines for Java Users Group Switzerland
 
## Starting the project
After a successful compilation, the Spring Boot app defined by `org.flowable.examples.spring.boot.FlowableSpringBootExampleApplication` 
can be started.

On startup it will automatically deploy the app present under `resources/apps/New Order.bar`. That app contains the 
CMMN and BPMN models shown at the demo. The app and its included models can be imported into the visual editor by using
the file `resources/apps/modeler/New Order.zip`.

Besides that, the application is already configured to expose the REST endpoints for the 6 Flowable engines:
* `process-api` for the Process Engine
* `cmmn-api` for the CMMN Engine
* `dmn-api` for the DMN Engine
* `idm-api` for the IDM Engine
* `form-api` for the Form Engine
* `content-api` for the Content Engine

## Creating a case
In order to trigger the creation of a new case, the file `requests/OrderRequests.http` contains several REST requests, 
execute the **first** one.

## Using flowable-admin to inspect and manage the engines
Download all Flowable applications as instructed here: https://flowable.com/open-source/downloads/. More information on 
how to configure and use them can be found in the documentation: https://flowable.com/open-source/docs/bpmn/ch14-Applications.

First start flowable-idm:

`java -jar flowable-idm.war`

Then start in a new console Flowable Admin. The following command will start it with the required parameters for this demo:

`java -Dflowable.admin.app.server-config.process.port=8090 -Dflowable.admin.app.server-config.process.context-root=/ -Dflowable.admin.app.server-config.cmmn.port=8090 -Dflowable.admin.app.server-config.cmmn.context-root=/ -Dflowable.admin.app.server-config.app.port=8090 -Dflowable.admin.app.server-config.app.context-root=/ -Dflowable.admin.app.server-config.dmn.port=8090 -Dflowable.admin.app.server-config.dmn.context-root=/ -Dflowable.admin.app.server-config.form.port=8090 -Dflowable.admin.app.server-config.form.context-root=/ -Dflowable.admin.app.server-config.content.port=8090 -Dflowable.admin.app.server-config.content.context-root=/ -jar flowable-admin.war`

Flowable Admin can be accessed under http://localhost:9988/flowable-admin.

## Starting flowable-modeler
Flowable Modeler comes bundled in the package you downloaded in the previous section. Start it as follows to be able to
deploy to the JUG Demo App:

`java -Dflowable.modeler.app.deployment-api-url=http://localhost:8090/app-api -jar flowable-modeler.war`

Flowable Modeler will now be accessible at http://localhost:8888/flowable-modeler.