/*
 * Copyright 2021 Andika Wasisto
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.wasisto.camrnghttp.servermanager.ui.about

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.wasisto.camrnghttp.R
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class AboutViewModelTest {

    private lateinit var aboutViewModel: AboutViewModel

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        aboutViewModel = AboutViewModel()
    }

    @Test
    fun onPrivacyPolicyButtonClick() {
        aboutViewModel.onPrivacyPolicyButtonClick()
        assertEquals(R.string.privacy_policy_url, aboutViewModel.launchBrowserEvent.value?.getContentIfNotHandled())
    }

    @Test
    fun onOpenSourceLicensesButtonClick() {
        aboutViewModel.onOpenSourceLicensesButtonClick()
        assertNotNull(aboutViewModel.launchOssLicensesMenuActivityEvent.value?.getContentIfNotHandled())
    }

    @Test
    fun onSourceCodeButtonClick() {
        aboutViewModel.onSourceCodeButtonClick()
        assertEquals(R.string.source_code_url, aboutViewModel.launchBrowserEvent.value?.getContentIfNotHandled())
    }
}