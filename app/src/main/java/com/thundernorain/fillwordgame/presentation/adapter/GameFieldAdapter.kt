package com.thundernorain.fillwordgame.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.thundernorain.fillwordgame.R
import com.thundernorain.fillwordgame.databinding.ItemGameFieldBinding
import com.thundernorain.fillwordgame.domain.model.ui_model.GameFieldItem
import com.thundernorain.fillwordgame.presentation.extensions.getDrawableCompat

class GameFieldAdapter(
    private val onItemClick: (item: GameFieldItem) -> Unit,
    private val gameField: MutableList<GameFieldItem> = mutableListOf(),
) : RecyclerView.Adapter<GameFieldAdapter.GameFieldViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameFieldViewHolder {
        val itemBinding = ItemGameFieldBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )
        return GameFieldViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: GameFieldViewHolder, position: Int) {
        val item = gameField[position]
        holder.bind(item, onItemClick)
    }

    override fun getItemCount() = gameField.size

    fun updateAllItems(newItems: List<GameFieldItem>) {
        gameField.clear()
        gameField.addAll(newItems)
        notifyDataSetChanged()
    }

    fun updateItem(item: GameFieldItem) {
        gameField.removeAt(item.position)
        gameField.add(item.position, item)
        notifyItemChanged(item.position)
    }

    class GameFieldViewHolder(
        private val binding: ItemGameFieldBinding,
    ) : RecyclerView.ViewHolder(binding.root) {

        fun bind(
            item: GameFieldItem,
            onItemClick: (item: GameFieldItem) -> Unit
        ) {
            with (binding) {
                item.position = bindingAdapterPosition
                letterTextView.text = item.letter.toString()
                setViewItemBackground(item)
                itemView.setOnClickListener {
                    onItemClick(item)
                }
            }
        }

        private fun setViewItemBackground(item: GameFieldItem) {
            with (binding.backgroundImageView) {
                if (item.isCorrect) {
                    setImageDrawable(
                        itemView
                            .getDrawableCompat(R.drawable.shape_rectangle_game_field_item_correct)
                    )
                    return
                }
                if (item.isSelected) {
                    setImageDrawable(
                        itemView
                            .getDrawableCompat(R.drawable.shape_rectangle_game_field_item_selected)
                    )
                } else {
                    setImageDrawable(
                        itemView
                            .getDrawableCompat(R.drawable.shape_rectangle_game_field_item_idle)
                    )
                }
            }
        }
    }
}