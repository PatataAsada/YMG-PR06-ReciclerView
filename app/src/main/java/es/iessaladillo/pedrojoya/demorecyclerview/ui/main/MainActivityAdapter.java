package es.iessaladillo.pedrojoya.demorecyclerview.ui.main;

import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import es.iessaladillo.pedrojoya.demorecyclerview.R;
import es.iessaladillo.pedrojoya.demorecyclerview.data.local.model.Student;

public class MainActivityAdapter extends ListAdapter<Student, MainActivityAdapter.ViewHolder> {
    private final OnEditStudentClickListener onEditStudentClickListener;
    private final OnDeleteStudentClickListener onDeleteStudentClickListener;

    public MainActivityAdapter(OnEditStudentClickListener onEditStudentClickListener, OnDeleteStudentClickListener onDeleteStudentClickListener) {
        super(new DiffUtil.ItemCallback<Student>() {
            @Override
            public boolean areItemsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getId() == newItem.getId();
            }

            @Override
            public boolean areContentsTheSame(@NonNull Student oldItem, @NonNull Student newItem) {
                return oldItem.getImageResId() == newItem.getImageResId() &&
                        TextUtils.equals(oldItem.getName(), newItem.getName()) &&
                        TextUtils.equals(oldItem.getEmail(), newItem.getEmail()) &&
                        oldItem.getPhonenumber() == newItem.getPhonenumber() &&
                        TextUtils.equals(oldItem.getAddress(), newItem.getAddress()) &&
                        TextUtils.equals(oldItem.getWeb(), newItem.getWeb());
            }
        });
        this.onEditStudentClickListener = onEditStudentClickListener;
        this.onDeleteStudentClickListener = onDeleteStudentClickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext())
                .inflate(R.layout.activity_main_item, parent, false), onEditStudentClickListener, onDeleteStudentClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.bind(getItem(position));
    }

    @Override
    public long getItemId(int position) {
        return getItem(position).getId();
    }

    @Override
    public Student getItem(int position) {
        return super.getItem(position);
    }

    @SuppressWarnings("WeakerAccess")
    static class ViewHolder extends RecyclerView.ViewHolder {

        private final ImageView imgAvatar;
        private final TextView lblName;
        private final TextView lblEmail;
        private final TextView lblPhonenumber;
        private final Button btnEdit;
        private final Button btnDelete;

        public ViewHolder(View itemView, final OnEditStudentClickListener onEditStudentListener, final OnDeleteStudentClickListener onDeleteStudentClickListener) {
            super(itemView);
            imgAvatar = ViewCompat.requireViewById(itemView, R.id.imgAvatar);
            lblName = ViewCompat.requireViewById(itemView, R.id.lblName);
            lblPhonenumber = ViewCompat.requireViewById(itemView, R.id.lblPhonenumber);
            lblEmail = ViewCompat.requireViewById(itemView, R.id.lblEmail);
            btnDelete = ViewCompat.requireViewById(itemView, R.id.btnDelete);
            btnEdit = ViewCompat.requireViewById(itemView, R.id.btnEdit);
            btnEdit.setOnClickListener(v -> onEditStudentListener.onItemClick(getAdapterPosition()));
            btnDelete.setOnClickListener(v -> onDeleteStudentClickListener.onItemClick(getAdapterPosition()));
        }

        public void bind(Student student) {
            imgAvatar.setImageResource(student.getImageResId());
            lblName.setText(student.getName());
            lblEmail.setText(student.getEmail());
            lblPhonenumber.setText(String.valueOf(student.getPhonenumber()));
        }
    }

}
