package com.example.productsapp.presentation.fragments

import android.content.Context
import android.graphics.Color
import android.net.ConnectivityManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.view.updatePadding
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.productsapp.databinding.CategoriesCardBinding
import com.example.productsapp.databinding.FragmentProductBinding
import com.example.productsapp.domain.entities.ProductEntity
import com.example.productsapp.presentation.ProductApplication
import com.example.productsapp.presentation.adapters.OnClickListener
import com.example.productsapp.presentation.adapters.ProductAdapter
import com.example.productsapp.presentation.viewmodels.ProductViewModel
import com.example.productsapp.presentation.viewmodels.ViewModelFactory
import javax.inject.Inject

class ProductFragment : Fragment(), OnClickListener {

    private var _binding: FragmentProductBinding? = null
    private val binding: FragmentProductBinding
        get() = _binding ?: throw RuntimeException("FragmentProductBinding == null")

    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    private lateinit var viewModel: ProductViewModel

    private val component by lazy {
        (requireActivity().application as ProductApplication).component
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentProductBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this, viewModelFactory)[ProductViewModel::class.java]
        val adapter = ProductAdapter(this)
        binding.rvAdapter.adapter = adapter
        binding.rvAdapter.layoutManager = GridLayoutManager(context, 1)
        addToolbar()
        showProducts(adapter)
        showProgressBar()
        showMoreProducts()
        addCategories(adapter)
        if (!isNetworkAvailable(requireContext())) {
            Toast.makeText(
                requireContext(),
                "Проверьте подключение к интернету!",
                Toast.LENGTH_SHORT
            ).show()
        } else {
            viewModel.loadMoreProducts()
            viewModel.getCategories()
        }
    }

    private fun addCategories(adapter: ProductAdapter) {
        viewModel.categoryList.observe(viewLifecycleOwner) { categories ->
            val filterViews = mutableListOf<CategoriesCardBinding>()
            val paddingInDp = 12
            val density = resources.displayMetrics.density
            val paddingInPx = (paddingInDp * density).toInt()

            for (tag in categories) {
                val filterBinding = CategoriesCardBinding.inflate(
                    layoutInflater,
                    binding.llFilter,
                    false
                )
                binding.llFilter.addView(filterBinding.root)
                filterBinding.tvCategory.text = tag.capitalize()
                filterBinding.filterCardView.setCardBackgroundColor(Color.parseColor("#F8F8F8"))
                filterBinding.tvCategory.setTextColor(Color.parseColor("#A0A1A3"))
                filterBinding.imageView4.visibility = View.GONE
                filterBinding.tvCategory.updatePadding(right = paddingInPx)
                filterViews.add(filterBinding)
            }

            viewModel.productListWithCategory.observe(viewLifecycleOwner) { product ->
                adapter.submitList(product)
            }

            for (filterBinding in filterViews) {
                filterBinding.filterCardView.setOnClickListener {
                    binding.customToolbar.tvScreenName.text = filterBinding.tvCategory.text.toString().capitalize()
                    filterBinding.tvCategory.setTextColor(Color.WHITE)
                    filterBinding.tvCategory.updatePadding(right = paddingInPx/2)
                    filterBinding.filterCardView.setCardBackgroundColor(Color.parseColor("#52606D"))
                    filterBinding.imageView4.visibility = View.VISIBLE
                    if (isNetworkAvailable(requireContext())) {
                        viewModel.getProductListWithCategory(filterBinding.tvCategory.text.toString())
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Проверьте подключение к интернету!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                    for (otherBinding in filterViews) {
                        if (otherBinding != filterBinding) {
                            otherBinding.filterCardView.setCardBackgroundColor(Color.parseColor("#F8F8F8"))
                            otherBinding.tvCategory.setTextColor(Color.parseColor("#A0A1A3"))
                            otherBinding.tvCategory.updatePadding(right = paddingInPx)
                            otherBinding.imageView4.visibility = View.GONE
                        }
                    }
                }

                filterBinding.imageView4.setOnClickListener {
                    for (otherBinding in filterViews) {
                        otherBinding.filterCardView.setCardBackgroundColor(Color.parseColor("#F8F8F8"))
                        otherBinding.tvCategory.setTextColor(Color.parseColor("#A0A1A3"))
                        otherBinding.tvCategory.updatePadding(right = paddingInPx)
                        otherBinding.imageView4.visibility = View.GONE
                    }
                    viewModel.setActiveCategoryList()
                    binding.customToolbar.tvScreenName.text = "Товары"
                    viewModel.productList.observe(viewLifecycleOwner) { product ->
                        adapter.submitList(product)
                    }
                }
            }
        }
    }



    private fun showProducts(adapter: ProductAdapter) {
        viewModel.productList.observe(viewLifecycleOwner) {
            adapter.submitList(it)
        }
    }

    private fun showMoreProducts() {
        binding.rvAdapter.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as GridLayoutManager
                val lastVisibleItemPosition = layoutManager.findLastVisibleItemPosition()
                val isLastItem =
                    lastVisibleItemPosition >= (recyclerView.adapter?.itemCount ?: 0) - 1
                if (isLastItem && !viewModel.isCategoryListActive.value!!) {
                    if (isNetworkAvailable(requireContext())) {
                        viewModel.loadMoreProducts()
                    } else {
                        Toast.makeText(
                            requireContext(),
                            "Проверьте подключение к интернету!",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }
            }
        })
    }

    private fun isNetworkAvailable(context: Context): Boolean {
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val networkInfo = connectivityManager.activeNetworkInfo
        return networkInfo != null && networkInfo.isConnected
    }

    private fun addToolbar() {
        binding.customToolbar.tvScreenName.text = "Товары"
    }

    private fun showProgressBar() {
        viewModel.progressBar.observe(viewLifecycleOwner) {
            if (it) {
                binding.progressBar.visibility = View.VISIBLE
            } else {
                binding.progressBar.visibility = View.INVISIBLE
            }
        }
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        component.inject(this)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onClickProduct(productEntity: ProductEntity) {
        findNavController().navigate(
            ProductFragmentDirections.actionProductFragmentToDetailInfoFragment(
                productEntity
            )
        )
    }
}