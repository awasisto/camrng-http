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

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.wasisto.camrnghttp.R
import com.wasisto.camrnghttp.servermanager.ui.Event

class AboutViewModel : ViewModel() {

    val launchBrowserEvent = MutableLiveData<Event<Int>>()

    val launchOssLicensesMenuActivityEvent = MutableLiveData<Event<Unit>>()

    fun onPrivacyPolicyButtonClick() {
        launchBrowserEvent.value = Event(R.string.privacy_policy_url)
    }

    fun onOpenSourceLicensesButtonClick() {
        launchOssLicensesMenuActivityEvent.value = Event(Unit)
    }

    fun onSourceCodeButtonClick() {
        launchBrowserEvent.value = Event(R.string.source_code_url)
    }
}