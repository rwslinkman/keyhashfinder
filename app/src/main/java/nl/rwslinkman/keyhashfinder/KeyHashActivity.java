package nl.rwslinkman.keyhashfinder;

import android.app.Activity;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.security.MessageDigest;


public class KeyHashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_keyhash);

        // Your package name is the package defined in line 1 of this file
        String packageName = "nl.rwslinkman.keyhashfinder";

        TextView yourHashView = (TextView) this.findViewById(R.id.yourHashView);
        TextView resultView = (TextView) this.findViewById(R.id.resultView);

        try
        {
            PackageInfo info = getPackageManager().getPackageInfo(packageName, PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures)
            {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());

                // This string contains your keyhash
                String keyHash = Base64.encodeToString(md.digest(), Base64.DEFAULT);

                // Your keyhash is shown this way
                resultView.setText(keyHash);
            }
        }
        catch(Exception e)
        {
            // In case of an error, the error will be shown
            yourHashView.setText(this.getErrorString());
            resultView.setText(e.getMessage());
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.key_hash, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        int id = item.getItemId();
        return id == R.id.action_settings || super.onOptionsItemSelected(item);
    }

    private String getErrorString()
    {
        return this.getResources().getString(R.string.errorMessage);
    }
}
