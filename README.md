# In the terminal to run:

`mvn spring-boot: run`

# There are tests for validate ssn endpoint (I've written a test for exchange as well but faced strange errors related to JSONObject). In the terminal to run tests:

`mvn test`

## OR

# to run through Docker compose
in the root of the project, run: `docker compose up`.

it will install the packages, run the test, and then run the server so you can send the requests to it. Uses port 8080

# After the startup (either through mvn or through docker compose)

it will be running on localhost port 8080

To see the formatted XML, send a POST request with 'Content-Type: application/xml' header to "http://localhost:8080/transform"

For example:
`curl --request GET \
  --url 'http://localhost:8080/exchange_amount?to=USD&from=SEK&amount=31'`

`curl --request GET \
  --url 'http://localhost:8080/exchange_amount?to=SEK&from=EUR&amount=31'`
returns true 
`curl --request POST \
  --url http://localhost:8080/validate_ssn \
  --header 'Content-Type: application/json' \
  --data '{
	"ssn":"131052-308T",
	"countryCode":"FI"
}'`
returns false with an error message
`curl --request POST \
  --url http://localhost:8080/validate_ssn \
  --header 'Content-Type: application/json' \
  --data '{
	"ssn":"131022-308D",
	"countryCode":"FI"
}'`
returns true
`curl --request POST \
  --url http://localhost:8080/validate_ssn \
  --header 'Content-Type: application/json' \
  --data '{
	"ssn":"080408A9879",
	"countryCode":"FI"
}'`
