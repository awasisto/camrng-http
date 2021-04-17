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

import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.oss.licenses.OssLicensesMenuActivity
import com.wasisto.camrnghttp.R
import com.wasisto.camrnghttp.databinding.ActivityAboutBinding

class AboutActivity : AppCompatActivity() {

    private lateinit var viewModel: AboutViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        viewModel = ViewModelProvider(this).get(AboutViewModel::class.java)

        DataBindingUtil.setContentView<ActivityAboutBinding>(this, R.layout.activity_about).apply {
            lifecycleOwner = this@AboutActivity
            viewModel = this@AboutActivity.viewModel
        }

        viewModel.launchBrowserEvent.observe(this) { event ->
            event.getContentIfNotHandled()?.let { stringResId ->
                startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(getString(stringResId))))
            }
        }

        viewModel.launchOssLicensesMenuActivityEvent.observe(this) { event ->
            if (!event.hasBeenHandled) {
                startActivity(Intent(this, OssLicensesMenuActivity::class.java))
                OssLicensesMenuActivity.setActivityTitle(getString(R.string.open_source_licenses))
            }
        }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> finish()
            else -> return super.onOptionsItemSelected(item)
        }
        return true
    }
}