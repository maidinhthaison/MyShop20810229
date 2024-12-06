package com.ql2.myshop.data

import com.ql2.myshop.domain.model.product.ProductModel

object ProductResponseMock {

    internal class MockGetAllProductResponseDTO {

        var productId: Int? = 1
        var cateId: Int? = 1
        var importPrice: Int? = 20000000
        var salePrice: Int? = 23500000
        var quantity: Int? = 9
        var description: String? = "Dell Latitude 5450 (2024)Core Ultra 5 - 135U RAM 16GB SSD 256GB 14-inch FHD Touch Windows 11"
        var productName: String? = "Dell Latitude 5450 (2024)"
        var productImage: String? = "dell_5450_2024.jpg@@dell_5450_2024.jpg@@dell_5450_2024.jpg"

        var results = listOf(
            ProductModel(
                productId = productId,
                cateId = cateId,
                importPrice = importPrice,
                salePrice = salePrice,
                quantity = quantity,
                description = description,
                productName = productName,
                productImage = productImage
            )
        )
            private set

    }
}