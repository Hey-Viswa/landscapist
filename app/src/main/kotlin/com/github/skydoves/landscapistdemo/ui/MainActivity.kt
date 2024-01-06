/*
 * Designed and developed by 2020-2023 skydoves (Jaewoong Eum)
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
package com.github.skydoves.landscapistdemo.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.github.skydoves.landscapistdemo.R
import com.github.skydoves.landscapistdemo.model.MockUtil
import com.github.skydoves.landscapistdemo.theme.DisneyComposeTheme
import com.github.skydoves.landscapistdemo.theme.background
import com.github.skydoves.landscapistdemo.theme.purple200
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

  private val vm: MainViewModel by viewModels()

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)

    setContent {
      DisneyComposeTheme {
        Scaffold(
          backgroundColor = if (isSystemInDarkTheme()) {
            background
          } else {
            Color.White
          },
          topBar = { PosterAppBar() },
        ) {
          val list = MockUtil.getMockPosters().toMutableList()
          list.addAll(MockUtil.getMockPosters())
          list.addAll(MockUtil.getMockPosters())
          DisneyPosters(posters = list, paddingValues = it, vm = vm)
        }
      }
    }
  }
}

@Composable
private fun PosterAppBar() {
  TopAppBar(
    elevation = 6.dp,
    backgroundColor = purple200,
    modifier = Modifier.height(58.dp),
  ) {
    Text(
      modifier = Modifier
        .padding(8.dp)
        .align(Alignment.CenterVertically),
      text = stringResource(R.string.app_name),
      color = Color.White,
      fontSize = 18.sp,
      fontWeight = FontWeight.Bold,
    )
  }
}
