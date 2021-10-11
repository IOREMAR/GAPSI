/*
 * Copyright (C) 2018 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.gapsiproyect

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import androidx.savedstate.SavedStateRegistryOwner
import com.example.gapsiproyect.Network.ProductsNetwork
import com.example.gapsiproyect.Repositories.ProductRepository
import com.example.gapsiproyect.Repositories.RoomRepository
import com.example.gapsiproyect.Utils.ViewModelFactory

/**
 * Class creadora de repositorios.
 */
object Injection {

    /**
     * Creates an instance of [ProductRepository] based on the [ProductsNetwork]
     *
     */
    private fun provideProductRepository(): ProductRepository {
        return ProductRepository(ProductsNetwork.create())
    }


    /**
     * Creates an instance of [ProductRepository] based on the [ProductsNetwork]
     *
     */
    private fun provideDBRepository(context: Context): RoomRepository {
        return RoomRepository(context)
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(owner: SavedStateRegistryOwner,context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(owner, provideProductRepository(),provideDBRepository(context))
    }
}
