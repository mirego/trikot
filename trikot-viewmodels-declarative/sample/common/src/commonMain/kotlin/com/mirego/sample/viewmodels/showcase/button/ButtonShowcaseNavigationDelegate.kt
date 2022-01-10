package com.mirego.sample.viewmodels.showcase.button

import com.mirego.sample.viewmodels.showcase.ShowcaseNavigationDelegate

interface ButtonShowcaseNavigationDelegate : ShowcaseNavigationDelegate {
    fun showMessage(text: String)
}
