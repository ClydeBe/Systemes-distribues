using System;
using System.Collections.Generic;
using System.Text;
using System.Windows;
using System.Windows.Controls;
using System.Windows.Data;
using System.Windows.Documents;
using System.Windows.Input;
using System.Windows.Media;
using System.Windows.Media.Imaging;
using System.Windows.Navigation;
using System.Windows.Shapes;

namespace GestionAlbum
{
    /// <summary>
    /// Logique d'interaction pour AlbumEdit.xaml
    /// </summary>
    public partial class AlbumEditView : UserControl
    {
        public AlbumEditView()
        {
            InitializeComponent();
        }

        private void AddTrackButton_Click(object sender, RoutedEventArgs e)
        {
            if (DataContext is AlbumViewModel a)
            {
                a.AddTrack();
                a.setNumber();
            }
        }

        private void RemoveTrackButton_Click(object sender, RoutedEventArgs e)
        {
            if (DataContext is AlbumViewModel a)
            {
                a.RemoveTrack();
                a.setNumber();
            }
        }
    }
}
