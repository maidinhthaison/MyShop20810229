package com.ql2.myshop.data

import com.ql2.myshop.domain.model.category.CategoryModel

object GetCategoryResponseMock {
    class MockUpdateProfileRequestDTO {

        var cateId: Int = 1
        var cateName: String = "DELL"
        var cateDescription: String = "DELL Brand Laptop"
        var results = listOf(
            CategoryModel(
                cateId = cateId,
                cateName = cateName,
                cateDescription = cateDescription
            )
        )
            private set

    }

}