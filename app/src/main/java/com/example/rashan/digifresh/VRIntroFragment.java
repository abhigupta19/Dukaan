package com.example.rashan.digifresh;

import android.content.Context;
    import android.net.Uri;
    import android.os.Bundle;
    import android.support.v4.app.Fragment;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;

/**
     * create an instance of this fragment.
     */
    public class VRIntroFragment extends Fragment {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private static final String ARG_FRAG = "fragId";

        // TODO: Rename and change types of parameters

        private OnFragmentInteractionListener mListener;

        private int fragToDisplayID = R.layout.fragment_intro1;

        public VRIntroFragment() {
            // Required empty public constructor
        }

        public static VRIntroFragment newInstance(int fragID) {
            VRIntroFragment fragment = new VRIntroFragment();
            Bundle args = new Bundle();
            args.putInt(ARG_FRAG, fragID);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                fragToDisplayID = getArguments().getInt(ARG_FRAG);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            // Inflate the layout for this fragment
            View v = inflater.inflate(fragToDisplayID, container, false);
            return v;
        }

        // TODO: Rename method, update argument and hook method into UI event
        public void onButtonPressed(Uri uri) {
            if (mListener != null) {
                mListener.onFragmentInteraction(uri);
            }
        }

        @Override
        public void onAttach(Context context) {
            super.onAttach(context);
            if (context instanceof OnFragmentInteractionListener) {
                mListener = (OnFragmentInteractionListener) context;
            } else {
                throw new RuntimeException(context.toString()
                        + " must implement OnFragmentInteractionListener");
            }
        }

        @Override
        public void onDetach() {
            super.onDetach();
            mListener = null;
        }

        /**
         * This interface must be implemented by activities that contain this
         * fragment to allow an interaction in this fragment to be communicated
         * to the activity and potentially other fragments contained in that
         * activity.
         * <p>
         * See the Android Training lesson <a href=
         * "http://developer.android.com/training/basics/fragments/communicating.html"
         * >Communicating with Other Fragments</a> for more information.
         */
        public interface OnFragmentInteractionListener {
            // TODO: Update argument type and name
            void onFragmentInteraction(Uri uri);
        }

}
