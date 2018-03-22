Project made for learning purposes and also as a kind of experiment.

It's a Spring application with RESTful API for storing To-Dos.
To-Dos comes in two types: Tasks and Projects. Tasks can be attached to Projects, although they don't have to be.
To-Dos are assigned to Users. User is a resource binding To-Do with API key.
Only admin type Users can create api keys for new Users, so the end-application can use Admin-type User account to provide end-users with their own user API keys.
This way logging logic is left up to the API user, but Users can be provided with their API key to aquire their stored To-Dos via some other application.

API key has to be provided in "Authorization" field of the header of every request.

Endpoints:
{braces} around parameter mean it is optional. 

GET /task
params: id
returns: XML-formatted data about Task with the given id.

GET /task/getAll
params: none
returns: XML-formatted data about all Tasks of the author with the matching API key.

POST /task
body: json-formatted Task object. 
Task's parameters: {id}, title, description, dateDue, outcome, {projectId}. 
Outcome can be: "NEW", "WIP", "FAILED", "SUCCEEDED", "FROZEN". Default value is "NEW". 
effect: saves new Task or updates the existing one, if the id is provided.
returns: XML-formatted data about saved Task with the assigned id.

PUT /task/attachToProject
params: id, projectId
effect: binds Task with id provided in param "id" with Project with id provided in param "projectId", if both exist and API key used to make the request matches the author of both.
returns: information about successful attaching action.

GET /project
params: id
returns: XML-formatted data about Project with the given id.

GET /task/getAll
params: none
returns: XML-formatted data about all Projects of the author with the matching API key.

POST /task
body: json-formatted Project object. 
Project's parameters: title, description, dateDue, outcome. 
Outcome can be: "NEW", "WIP", "FAILED", "SUCCEEDED", "FROZEN". Default value is "NEW". 
effect: saves new Project or updates the existing one, if the id is provided.
returns: XML-formatted data about saved Task with the assigned id.

GET /getKey
params: none
returns: new API key attached to a new User, but ONLY if the Admin-type API key is provided in the "Authorization" field of the request header.