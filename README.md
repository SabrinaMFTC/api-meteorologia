# InfluxDB

## Docker Compose

1 - To run **InfluxDB** locally, use the following `docker-compose.yaml` file:

```yaml
services:
  influxdb2:
    image: influxdb:2
    ports:
      - 8086:8086
    environment:
      DOCKER_INFLUXDB_INIT_MODE: setup
      DOCKER_INFLUXDB_INIT_USERNAME_FILE: /run/secrets/influxdb2-admin-username
      DOCKER_INFLUXDB_INIT_PASSWORD_FILE: /run/secrets/influxdb2-admin-password
      DOCKER_INFLUXDB_INIT_ADMIN_TOKEN_FILE: /run/secrets/influxdb2-admin-token
      DOCKER_INFLUXDB_INIT_ORG: docs
      DOCKER_INFLUXDB_INIT_BUCKET: home
    secrets:
      - influxdb2-admin-username
      - influxdb2-admin-password
      - influxdb2-admin-token
    volumes:
      - type: volume
        source: influxdb2-data
        target: /var/lib/influxdb2
      - type: volume
        source: influxdb2-config
        target: /etc/influxdb2
secrets:
  influxdb2-admin-username:
    file: ~/.env.influxdb2-admin-username
  influxdb2-admin-password:
    file: ~/.env.influxdb2-admin-password
  influxdb2-admin-token:
    file: ~/.env.influxdb2-admin-token
volumes:
  influxdb2-data:
  influxdb2-config:
```

2 - Create the necessary `secrets` files in their respective locations (for example, for username create a file called `influxdb2-admin-username`, write the username you want on it (e.g. `admin`) and save it in `~/.env.influxdb2-admin-username`).

3 - Open your browser in `localhost:8086` - you'll see InfluxDB interface.

4 - Login with your admin and password (the ones you previously created as `secrets`).

## Token

In the left pannel, click on the upload arrow symbol >> `API TOKENS` and generate a new token or clone an existing token.

In our test we will be using the admin token we generated: `DDOpMZBXx3E481t3G1p3caeFxlNXG49zx6Vk6ZUZTwoMemEVBADCb4Kyr74MYnYQrB2qS_mU-joasJHAYB-Ubg==`.

## GET

To see the organization's ID use the command

```bash
curl --request GET "http://localhost:8086/api/v2/orgs" \
--header "Authorization: Token <YOUR-TOKEN-HERE>"
```

Replace <YOUR-TOKEN-HERE> with your token. In our case it will be

```bash
curl --request GET "http://localhost:8086/api/v2/orgs" \
--header "Authorization: Token DDOpMZBXx3E481t3G1p3caeFxlNXG49zx6Vk6ZUZTwoMemEVBADCb4Kyr74MYnYQrB2qS_mU-joasJHAYB-Ubg=="
```

You'see lots of data, including `id`, which is your organization's ID. In our case, it's `4190a0ea6c69b146`.

To GET the buckets from the API, enter the command 

```bash
curl --request GET "http://localhost:8086/api/v2/buckets" --header "Authorization: Token <YOUR-TOKEN-HERE>"
```

With our token

```bash
curl --request GET "http://localhost:8086/api/v2/buckets" --header "Authorization: Token DDOpMZBXx3E481t3G1p3caeFxlNXG49zx6Vk6ZUZTwoMemEVBADCb4Kyr74MYnYQrB2qS_mU-joasJHAYB-Ubg==" 
```

You'll see all the buckets in our DB.

## POST

To create a bucket, enter the command

```bash
curl --request POST "http://localhost:8086/api/v2/query?org=4190a0ea6c69b146" \
  --header "Authorization: Token <YOUR-TOKEN-HERE>" \
  --header "Content-Type: application/vnd.flux" \
  --data '{
    "query": "from(bucket: \"my_new_bucket\") |> range(start: -1h)"
  }'
```

Using our token:

```bash
curl --request POST "http://localhost:8086/api/v2/query?org=4190a0ea6c69b146" \
  --header "Authorization: Token DDOpMZBXx3E481t3G1p3caeFxlNXG49zx6Vk6ZUZTwoMemEVBADCb4Kyr74MYnYQrB2qS_mU-joasJHAYB-Ubg==" \
  --header "Content-Type: application/vnd.flux" \
  --data '{
    "query": "from(bucket: \"my_new_bucket\") |> range(start: -1h)"
  }'
```

To write in a bucket you need to use `/write` and inform the data you want to post

```bash
curl --request POST "http://localhost:8086/api/v2/write?org=4190a0ea6c69b146&bucket=my_new_bucket&precision=ns" \
    --header "Authorization: Token DDOpMZBXx3E481t3G1p3caeFxlNXG49zx6Vk6ZUZTwoMemEVBADCb4Kyr74MYnYQrB2qS_mU-joasJHAYB-Ubg==" \
    --header "Content-Type: text/plain; charset=utf-8" \
    --data 'sensor_data,sensor_id=TLM0201 temperature=23.5,humidity=60'
```



## 