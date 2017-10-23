package android.os;

/**
 * A synchronous implementation of android.os.AsyncTask that simplifies unit testing of asynchronous logic.
 *
 * See http://ryanharter.com/blog/2015/12/28/dealing-with-asynctask-in-unit-tests/ for more details.
 */
public abstract class AsyncTask<Params, Progress, Result> {

    protected abstract Result doInBackground(Params... params);

    protected void onPostExecute(Result result) {
    }

    protected void onProgressUpdate(Progress... values) {
    }

    public AsyncTask<Params, Progress, Result> execute(Params... params) {
        Result result = doInBackground(params);
        onPostExecute(result);
        return this;
    }

}