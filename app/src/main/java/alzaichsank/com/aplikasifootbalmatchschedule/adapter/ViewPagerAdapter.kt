package alzaichsank.com.aplikasifootbalmatchschedule.adapter

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter


class ViewPagerAdapter(manager: FragmentManager): FragmentPagerAdapter(manager) {
    private val mFragmentList: MutableList<Fragment> = mutableListOf()
    private val mFragmentTitleList: MutableList<String> = mutableListOf()

    override fun getItem(position: Int) = mFragmentList.get(position)

    override fun getCount() = mFragmentList.size

    fun addFrag(fragment: Fragment, title: String){
        mFragmentList.add(fragment)
        mFragmentTitleList.add(title)
    }

    override fun getPageTitle(position: Int) = mFragmentTitleList.get(position)
}