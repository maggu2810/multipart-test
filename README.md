# Multipart Test

For the related discussion see [this thread](https://lists.apache.org/thread.html/r8f214ea9ec0d37fcc7a69cbec38d9f5d9020954da60eb0e0a1f85ce8%40%3Cuser.karaf.apache.org%3E) on the Karaf user mailing list.

## Instruction

First clone this repository and enter the directory.

### Build

```
mvn clean install
```

### Test

* start Karaf 4.2.7 (or the version you want to test)
* install the "scr" and "http" feature: `feature:install scr http`
* install and start the bundle: `bundle:install file:///path/to/jar/servlet-1.0-SNAPSHOT.jar`
* upload a file using a POST request to "http://127.0.0.1/8181/upload"
  you could use e.g. curl

```
echo "test" > /tmp/test-multipart
curl --progress-bar -v -k -F file=/tmp/test-multipart http://127.0.0.1:8181/upload
```
