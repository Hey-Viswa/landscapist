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
package com.skydoves.landscapist.fresco

import android.graphics.Bitmap
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.asImageBitmap
import com.facebook.common.references.CloseableReference
import com.facebook.imagepipeline.image.CloseableImage
import com.skydoves.landscapist.ImageLoadState
import com.skydoves.landscapist.ImageState

/** GlideImageState represents the image loading states for Fresco. */
@Stable
public sealed class FrescoImageState : ImageState {

  /** Request not started. */
  @Stable
  public data object None : FrescoImageState()

  /** Request is currently in progress. */
  @Stable
  public data object Loading : FrescoImageState()

  /** Request is completed successfully and ready to use an [ImageBitmap]. */
  @Immutable
  public data class Success(
    val imageBitmap: ImageBitmap?,
    val dataSource: com.skydoves.landscapist.DataSource,
  ) : FrescoImageState()

  /** Request failed. */
  @Immutable
  public data class Failure(
    val closeableImage: CloseableImage?,
    val reason: Throwable?,
  ) : FrescoImageState()
}

/** casts an [ImageLoadState] type to a [FrescoImageState]. */
@Suppress("UNCHECKED_CAST")
@Composable
public fun ImageLoadState.toFrescoImageState(): FrescoImageState {
  return when (this) {
    is ImageLoadState.None -> FrescoImageState.None
    is ImageLoadState.Loading -> FrescoImageState.Loading
    is ImageLoadState.Success -> {
      val bitmapRef = data as? CloseableReference<Bitmap>
      val imageBitmap = if (bitmapRef != null) {
        rememberCloseableRef(bitmapRef).asImageBitmap()
      } else {
        null
      }
      FrescoImageState.Success(
        imageBitmap = imageBitmap,
        dataSource = dataSource,
      )
    }

    is ImageLoadState.Failure -> {
      val bitmapRef = data as? CloseableReference<CloseableImage>
      val closeableImage = if (bitmapRef != null) {
        rememberCloseableRef(bitmapRef)
      } else {
        null
      }
      FrescoImageState.Failure(
        closeableImage = closeableImage,
        reason = reason,
      )
    }
  }
}
