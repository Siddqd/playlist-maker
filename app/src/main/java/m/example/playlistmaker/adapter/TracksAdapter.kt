package m.example.playlistmaker.adapter
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import m.example.playlistmaker.model.Track
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import m.example.playlistmaker.R
import org.w3c.dom.Text

class TracksAdapter(
    private val tracks: List<Track>
) : RecyclerView.Adapter<TracksAdapter.TracksViewHolder> () {

    class TracksViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        private val trackName: TextView = itemView.findViewById(R.id.track_name)
        private val trackImage: ImageView = itemView.findViewById(R.id.track_image)
        private val trackArtistTime: TextView = itemView.findViewById(R.id.artist_and_time)

        //добавить вместо mage view Glide ??? и через него крепить ссылки
        fun bind(model: Track) {
            Glide.with(itemView)
                .load(model.artworkUrl100)
                .placeholder(R.drawable.no_img_track_24)
                .into(trackImage)
            trackName.text = model.trackName
            trackArtistTime.text = model.artistName + " * " + model.trackTime
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TracksViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.track_view, parent, false)
        return TracksViewHolder(view)
    }

    override fun onBindViewHolder(holder: TracksViewHolder, position: Int) {
        holder.bind(tracks[position])
    }

    override fun getItemCount(): Int {
        return tracks.size
    }

}


