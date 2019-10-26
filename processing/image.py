import boto3
import os

if __name__ == "__main__":

    # Change bucket and photo to your S3 Bucket and image.
    bucket = 'bucket'
    photo = 'photo.jpg'

    session = boto3.Session(
        aws_access_key_id=os.getenv('AWS_SERVER_PUBLIC_KEY'),
        aws_secret_access_key=os.getenv('AWS_SERVER_SECRET_KEY'),
    )

    client=boto3.client('rekognition',
                        region_name='us-west-2',
                        aws_access_key_id=os.getenv('AWS_SERVER_PUBLIC_KEY'),
                        aws_secret_access_key=os.getenv('AWS_SERVER_SECRET_KEY'),)

    response = client.detect_labels(Image={'S3Object': {'Bucket': bucket, 'Name': photo}})

    print('Detected labels for ' + photo)
    print()
    for label in response['Labels']:
        print("Label: " + label['Name'])
        print("Confidence: " + str(label['Confidence']))
        print("Instances:")
        for instance in label['Instances']:
            print("  Bounding box")
            print("    Top: " + str(instance['BoundingBox']['Top']))
            print("    Left: " + str(instance['BoundingBox']['Left']))
            print("    Width: " + str(instance['BoundingBox']['Width']))
            print("    Height: " + str(instance['BoundingBox']['Height']))
            print("  Confidence: " + str(instance['Confidence']))
            print()

        print("Parents:")
        for parent in label['Parents']:
            print("   " + parent['Name'])
        print("----------")
        print()
