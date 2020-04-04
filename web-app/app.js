const express = require('express')
const app = express()
const router = express.Router()

router.get('/hello-world', (req, res) => {
  return res.json('Hello World')
})

app.use('/api', router)

const PORT = 8080
const HOST = '0.0.0.0'
app.listen(PORT, HOST)

console.log(`Running on http://${HOST}:${PORT}`)