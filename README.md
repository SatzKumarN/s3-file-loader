# s3-file-loader

###### To Build ######

Built the project using 

```
mvn clean install
```
It will create two jars in the target folder. _ s3-file-loader-0.0.1.jar _ and _ uber-s3-file-loader-0.0.1.jar _

###### Prerequisite ######
Create aws.properties with the following keys

```
AWS_ACCESS_KEY_ID=
AWS_SECRET_ACCESS_KEY=
AWS_REGION=
AWS_BUCKET_NAME=
```

###### For Upload ######
```
java -jar uber-s3-file-loader-0.0.1.jar upload [absolute_path_of_aws.properties] [file_name_to_be_uploaded_in_s3_bucket] [absolute_path_of_local_file_to_be_uploaded]
```
e.g:

```
java -jar uber-s3-file-loader-0.0.1.jar upload /data/aws.properties file_in_s3.tar.gz /data/file_to_upload.tar.gz
```

Once the upload is complete we can check in S3 console. The file will be present in the given bucket with the 
_file_in_s3.tar.gz_
 name.

###### For Download ######
```
java -jar uber-s3-file-loader-0.0.1.jar download [absolute_path_of_aws.properties] [file_name_to_be_downloaded_from_s3_bucket] [absolute_path_of_file_to_be_downloaded]
```
e.g:

```
java -jar uber-s3-file-loader-0.0.1.jar download /data/aws.properties file_in_s3.tar.gz /data/local_file.tar.gz
```

Once download is complete the 
_file_in_s3.tar.gz_
 is downloaded from the S3 bucket and will be available in the local folder 
 _/data/local_file.tar.gz_
 .
