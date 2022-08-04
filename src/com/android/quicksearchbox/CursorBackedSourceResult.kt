/*
 * Copyright (C) 2022 The Android Open Source Project
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
package com.android.quicksearchbox

import android.content.ComponentName
import com.android.quicksearchbox.google.GoogleSource

class CursorBackedSourceResult(
    @get:Override override val suggestionSource: GoogleSource,
    userQuery: String?,
    cursor: Cursor?
) : CursorBackedSuggestionCursor(userQuery, cursor), SourceResult {
    constructor(source: GoogleSource, userQuery: String?) : this(source, userQuery, null)

    override val suggestionIntentComponent: ComponentName
        @Override get() = suggestionSource.getIntentComponent()
    override val isSuggestionShortcut: Boolean
        get() = false
    override val isHistorySuggestion: Boolean
        get() = false

    @Override
    override fun toString(): String {
        return suggestionSource.toString() + "[" + getUserQuery() + "]"
    }

    override val extras: SuggestionExtras
        @Override get() = if (mCursor == null) null else CursorBackedSuggestionExtras.createExtrasIfNecessary(
            mCursor,
            getPosition()
        )!!
    override val extraColumns: Collection<String>?
        get() = if (mCursor == null) null else CursorBackedSuggestionExtras.getExtraColumns(
            mCursor
        )
}