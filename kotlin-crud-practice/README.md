# 動かし方

## 環境の構築

`env/docker` に移動して、`docker-compose up -d` を実行する。

## アプリの起動

`kotlin-crud-practice` ディレクトリ配下で `./gradlew bootRun` を実行。 


## APIの実行方法

### データの取得（全件）

```
curl http://localhost:8080/user 
```

### データの取得（個別）

```
curl http://localhost:8080/user/${id} 
```

### データの登録
```
curl -v -X POST -H 'Content-Type:application/json' -d '{"firstName":"テスト姓","lastName":"テスト名","birthDay":"1986-12-16"}' http://localhost:8080/user
```


### データの更新
```
curl -v -X PUT -H 'Content-Type:application/json' -d '{"firstName":"更新姓","lastName":"更新名","birthDay":"1980-01-02"}' http://localhost:8080/user/1
```