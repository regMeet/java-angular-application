# front-end-angular-client

mvn archetype:generate -DgroupId=com.primatemonkeys -DartifactId=front-end-angular-client -DarchetypeArtifactId=maven-archetype-webapp -DinteractiveMode=false


https://coderwall.com/p/0wx6ca/angularjs-table-with-paging-filter-and-sorting-backed-by-rails


[{"idUser":1,"name":"Alan","lastname":null,"email":"alan@email.com","username":"regMeet","password":"admin","telephone":null,"mobile":null},
{"idUser":2,"name":"test","lastname":null,"email":"test@email.com","username":"test","password":"testing","telephone":null,"mobile":null}]


var r = $resource('/users/:id',null,   {'update': { method:'PUT' }});
r.query()  //does GET on /users and gets all users
r.get({id:1}) // does GET on /users/1 and gets a specific user
r.save(userObject)  // does a POST to /users to save the user
r.update({ id:1 }, userObject) // Not defined by default but does PUT to /users/1 with user object.