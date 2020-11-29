const app = require('../../backend_server')
const supertest = require('supertest')
const { getUserOrder } = require('../../routes/orders')
const { getMenu, addToMenu } = require('../../routes/items')
const { getRecommendation } = require('../../recommendation_logic')
const { addOrderedItems, getOrderedItems } = require('../../routes/ordered_items')
const { getStripeKey, createStripePayment } = require('../../routes/payment')
const request = supertest(app)

let restaurantId = 2
let name = "testItem" 
let type = "sushi"
let cost = "12.5"
let description = "abc" 
let calories = "123"
let popularityCount = "0"
let image = "" 

async function testAddToMenu() {
    //Arrange
    let req_body =
        {
            "restaurantId" : restaurantId ,
            "name" : name ,
            "type" : type,
            "cost" : cost,
            "description" : description ,
            "calories" : calories,
            "popularityCount" : popularityCount,
            "image" : image 
        }
 
    let url = "/items"

    //Act
    const res = await request.post(url).send(req_body)

    //Assert
    expect(res.status).toBe(200)
}

async function testGetMenuLatestItem() {
    // Arrange
    const url = `/items/${restaurantId}`

    // Act
    const response = await request.get(url)

    // Assert
    expect(response.status).toBe(200)

    expect(response.body[response.body.length-1].id).toStrictEqual(expect.anything())
    expect(response.body[response.body.length-1].restaurant_id).toStrictEqual(restaurantId)
    expect(response.body[response.body.length-1].name).toStrictEqual(name)
    expect(response.body[response.body.length-1].type).toStrictEqual(type)
    expect(String(response.body[response.body.length-1].cost)).toStrictEqual(String(cost))
    expect(response.body[response.body.length-1].description).toStrictEqual(description)
    expect(String(response.body[response.body.length-1].calories)).toStrictEqual(String(calories))
    expect(String(response.body[response.body.length-1].popularity_count)).toStrictEqual(String(popularityCount))
    expect(response.body[response.body.length-1].image).toStrictEqual(image)

}

describe("Integration test 5: ", () => {
    it("Add Item to Menu and verify", async done => {
        await testAddToMenu()
        await testGetMenuLatestItem()
        done()
    })
})