export MICROFAB_CONFIG='{
    "endorsing_organizations":[
        {
            "name": "SampleOrg"
        }
    ],
    "channels":[
        {
            "name": "mychannel",
            "endorsing_organizations":[
                "SampleOrg"
            ]
        }
    ],
    "couchdb":false,
    "timeout":"60s"
}'

docker run -p 8080:8080 -e MICROFAB_CONFIG ibmcom/ibp-microfab