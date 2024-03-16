## Cloud-Nine (Ping Edition)

This repo contains a POC agent for Ping Gateway.

## Building

To build this repo, we'll need a few libraries from Ping to be placed into `./lib`.

We've not included these as part of this repo (don't need no DMCA issues), so instead we need to add them manually. The easiest way to do this will be:

```
docker pull pingidentity/pingone-ldap-gateway:2.3.4
id=$(docker create pingidentity/pingone-ldap-gateway:2.3.4)
docker cp $id:/gateway/gateway.jar /tmp/
unzip /tmp/gateway.jar ./libs/
```

Then we can build the project with IntelliJ IDEA.


