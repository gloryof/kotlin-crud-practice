db.createUser(
  {
    user:"crud-user",
    pwd:"crud-user",
    roles: [ "readWrite" ]
  }
)

db.createCollection(
    "sequence"
)

db.sequence.insertOne({ name: "user_sequence", value: 1})