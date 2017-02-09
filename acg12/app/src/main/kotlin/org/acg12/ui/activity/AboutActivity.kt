package org.acg12.ui.activity

import android.os.Bundle
import android.view.View
import org.acg12.config.Constant
import org.acg12.ui.base.PresenterActivityImpl
import org.acg12.views.AboutView

class AboutActivity : PresenterActivityImpl<AboutView>(), View.OnClickListener {

    override fun created(savedInstance: Bundle) {
        super.created(savedInstance)
    }

    override fun onClick(v: View) {
        val id = v.id
        if (id == Constant.TOOLBAR_ID) {
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}
