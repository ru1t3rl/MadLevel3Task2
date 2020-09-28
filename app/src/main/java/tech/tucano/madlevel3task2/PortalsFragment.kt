package tech.tucano.madlevel3task2

import android.net.Uri
import android.os.Bundle
import android.provider.ContactsContract.CommonDataKinds.Website.URL
import android.text.method.Touch
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.GridLayout
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.setFragmentResultListener
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import tech.tucano.madlevel3task2.databinding.FragmentPortalsBinding

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PortalsFragment : Fragment() {
    private val portals = arrayListOf<Portal>()
    private lateinit var portalAdapter: PortalAdapter
    private lateinit var customTabsIntent: CustomTabsIntent


    private lateinit var binding: FragmentPortalsBinding

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentPortalsBinding.inflate(inflater, container, false)

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.fab.setOnClickListener {
            findNavController().navigate(R.id.action_portalsFragment_to_addPortalFragment)
        }

        observeAddPortalResult()
        initViews()
    }

    private fun initViews() {
        binding.rvPortals.layoutManager = GridLayoutManager(context, 2)

        portalAdapter = PortalAdapter(portals) { portal: Portal ->
            onPortalClicked(
                portal
            )
        }

        binding.rvPortals.adapter = portalAdapter

        setupBrowser()
    }

    private fun setupBrowser() {
        val builder = CustomTabsIntent.Builder()
        builder.addDefaultShareMenuItem()
        builder.setShowTitle(true)
        builder.setStartAnimations(
            requireContext(),
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )
        builder.setExitAnimations(
            requireContext(),
            android.R.anim.slide_in_left,
            android.R.anim.slide_out_right
        )

        customTabsIntent = builder.build()
    }

    private fun onPortalClicked(portal: Portal) {
        // Http(s):// check to prevent unwanted crashes
        val urlRegex = getString(R.string.url_regex).toRegex()

        if(!urlRegex.containsMatchIn(portal.url)){
            portal.url = "http://"+portal.url
        }

        customTabsIntent.launchUrl(requireContext(), Uri.parse(portal.url))
    }

    private fun observeAddPortalResult() {
        setFragmentResultListener(REQ_PORTAL_KEY) { key, bundle ->
            bundle.getParcelable<Portal>(BUNDLE_PORTAL_KEY)?.let {
                portals.add(it)

                portalAdapter.notifyDataSetChanged()
            }
        }
    }
}