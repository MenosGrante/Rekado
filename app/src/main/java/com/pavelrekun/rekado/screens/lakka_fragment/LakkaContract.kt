package com.pavelrekun.rekado.screens.lakka_fragment

 interface LakkaContract {

     interface View {

         fun initViews()

         fun initCBFSCategory()

         fun initCorebootCategory()

         fun initClickListeners()

         fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray)
     }

}